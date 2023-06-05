FROM eclipse-temurin:11-alpine
COPY /out/artifacts/hwp_converter_api_jar/hwp-converter-api.jar /hwp-converter-api.jar
RUN mkdir sample_hwp

EXPOSE 7000

ENTRYPOINT ["java", "-jar", "./hwp-converter-api.jar"]
