FROM tomcat:alpine

ENV DOCKER="True"
ENV POSTGRES_HOST=postgres
ENV POSTGRES_PORT=5432
ENV POSTGRES_DATABASE=activage-maintenance-panel
ENV POSTGRES_USER=activage
ENV POSTGRES_PASSWORD=activage

RUN rm -rf /usr/local/tomcat/webapps/*
ADD target/maintenance-panel.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]
