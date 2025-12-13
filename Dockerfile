# استخدم base image ثابت ومضمون
FROM eclipse-temurin:17-jre-alpine

# إنشاء مجلدات التطبيق ومجلد SSL
RUN mkdir -p /deployments/ssl

# تحديد environment variable لل keystore
ENV KEYSTORE_PATH=/deployments/ssl/keystore.jks
ENV JAVA_OPTS=""

# تحديد working directory
WORKDIR /deployments

# إنشاء user غير root
RUN addgroup -S vfuser && adduser -S -G vfuser vfuser

# نسخ ملفات التطبيق
COPY service/target/quarkus-app/lib/ ./lib/
COPY service/target/quarkus-app/*.jar ./
COPY service/target/quarkus-app/app/ ./app/
COPY service/target/quarkus-app/quarkus/ ./quarkus/

# إعطاء صلاحيات للـ user
RUN chown -R vfuser:vfuser /deployments

# الانتقال للـ user الجديد
USER vfuser

# فتح البورت 8080
EXPOSE 8080

# ENTRYPOINT مع exec لتعامل صحيح مع signals
ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS} -Djavax.net.ssl.keyStore=${KEYSTORE_PATH} -Djavax.net.ssl.keyStorePassword=${keystorePass} -jar ./quarkus-run.jar -Dquarkus.http.host=0.0.0.0"]
