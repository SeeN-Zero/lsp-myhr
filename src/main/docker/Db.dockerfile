FROM postgres:alpine
LABEL authors="senna"

COPY *.sql /docker-entrypoint-initdb.d/

