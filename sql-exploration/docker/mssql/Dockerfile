FROM mcr.microsoft.com/mssql/server:2017-latest-ubuntu

COPY init.sh /usr/local/bin/init.sh
COPY init.sql /init.sql

RUN ["chmod", "+x", "/usr/local/bin/init.sh"]
ENTRYPOINT [ "/usr/local/bin/init.sh" ]
