package ie.vodafone.dxl.checkservicefeasibility.utils;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.Failure;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.ResultStatus;
import ie.vodafone.dxl.utils.common.StringUtils;
import org.apache.cxf.binding.soap.SoapHeader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.ws.BindingProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SoapUtils {

    public static SoapHeader getSoapHeader(BindingProvider bindingProvider, String name) {
        if (bindingProvider == null || StringUtils.isBlank(name)) {
            return null;
        }
        SoapHeader soapHeader;
        try {
            List<SoapHeader> soapHeaderList = (List<SoapHeader>) bindingProvider.getResponseContext().get(Constants.Soap.HEADERS_LIST);
            soapHeader = soapHeaderList.stream()
                    .filter(Objects::nonNull)
                    .filter(header -> header.getName() != null && name.equals(header.getName().getLocalPart()))
                    .findAny()
                    .orElse(null);
        } catch (ClassCastException ex) {
            soapHeader = null;
        }
        return soapHeader;
    }

    public static ResultStatus getResultStatusFromSoapHeader(BindingProvider bindingProvider) {
        SoapHeader resultStatusHeader = getSoapHeader(bindingProvider, Constants.Soap.RESULT_STATUS);
        if (resultStatusHeader == null || resultStatusHeader.getObject() == null) {
            return null;
        }

        Element elementNS = (Element) resultStatusHeader.getObject();
        Optional<Node> parentNode = Optional.ofNullable(elementNS)
                .map(Node::getFirstChild)
                .map(Node::getNextSibling);

        if (parentNode.isEmpty()) {
            return null;
        }

        ResultStatus resultStatus = new ResultStatus();
        List<Failure> failures = new ArrayList<>();
        mapNodeFailuresIfExists(failures, parentNode.get().getNextSibling().getNextSibling());
        resultStatus.setName(parentNode.get().getFirstChild().getTextContent());
        resultStatus.setFailures(failures);
        return resultStatus;
    }

    private static void mapNodeFailuresIfExists(List<Failure> failures, Node node) {
        if (node == null || node.getFirstChild() == null || node.getFirstChild().getNextSibling() == null) {
            return;
        }

        Node nodeFailures = node.getFirstChild().getNextSibling();

        while (nodeFailures != null) {
            if (Constants.Soap.FAILURE.equals(nodeFailures.getLocalName())) {
                Failure failure = new Failure();
                failure.setCode(getStatusMessageFromHeader(nodeFailures));
                failure.setText(getOperationMessageFromHeader(nodeFailures));
                setDataRef(failure, nodeFailures);
                if (StringUtils.isNotBlank(failure.getCode()) || StringUtils.isNotBlank(failure.getText())) {
                    failures.add(failure);
                }

                if (nodeFailures.getNextSibling() != null && nodeFailures.getNextSibling().getNextSibling() != null) {
                    nodeFailures = nodeFailures.getNextSibling().getNextSibling();
                } else {
                    nodeFailures = null;
                }
            } else if (nodeFailures.getNextSibling() != null) {
                nodeFailures = nodeFailures.getNextSibling();
            } else {
                nodeFailures = null;
            }
        }
    }

    private static void setDataRef(Failure failure, Node failureNode) {

        Optional<Node> node = Optional.ofNullable(failureNode)
                .map(Node::getFirstChild)
                .map(Node::getNextSibling)
                .map(Node::getNextSibling)
                .map(Node::getNextSibling)
                .map(Node::getNextSibling)
                .map(Node::getNextSibling);

        if (node.isPresent() && Constants.Soap.DATA_REF.equalsIgnoreCase(node.get().getLocalName())) {
            Node pathNameNode = node.get().getFirstChild().getNextSibling();
            if (Constants.Soap.PATH_NAME.equalsIgnoreCase(pathNameNode.getLocalName())) {
                failure.setPathName(pathNameNode.getFirstChild().getTextContent());
                Node pathValueTextNode = pathNameNode.getNextSibling().getNextSibling();
                if (Constants.Soap.PATH_VALUE_TEXT.equalsIgnoreCase(pathValueTextNode.getLocalName())) {
                    failure.setPathValueText(pathValueTextNode.getFirstChild().getTextContent());
                }
            }
        }
    }

    private static String getStatusMessageFromHeader(Node failureNode) {
        String status = "";

        Optional<String> result = Optional.ofNullable(failureNode)
                .map(Node::getFirstChild)
                .map(Node::getNextSibling)
                .map(Node::getFirstChild)
                .map(Node::getTextContent);

        if (result.isPresent()) {
            status = result.get();
        }
        return status;
    }

    private static String getOperationMessageFromHeader(Node failureNode) {
        String operation = "";

        Optional<String> result = Optional.ofNullable(failureNode)
                .map(Node::getFirstChild)
                .map(Node::getNextSibling)
                .map(Node::getNextSibling)
                .map(Node::getNextSibling)
                .map(Node::getFirstChild)
                .map(Node::getTextContent);

        if (result.isPresent()) {
            operation = result.get();
        }
        return operation;
    }
}
