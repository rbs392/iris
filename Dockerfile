FROM openjdk:8

COPY ./build/iris-1.0 /app/iris
COPY ./bin/start.sh /app/iris/start.sh

WORKDIR /app/iris

ENV HOST=localhost PORT=9200

RUN \
  apt-get update && apt-get install --no-install-recommends -y jq && \
  cd /tmp && \
  wget https://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-1.0.1.tar.gz && \
  tar xvzf elasticsearch-1.0.1.tar.gz && \
  rm -f elasticsearch-1.0.1.tar.gz && \
  mv /tmp/elasticsearch-1.0.1 /app/elasticsearch && \
  cd /app/elasticsearch && \
  bin/plugin -install elasticsearch/elasticsearch-mapper-attachments/2.0.0 && \
  bin/plugin -install com.github.kzwang/elasticsearch-image/1.2.0

CMD ["sh", "./start.sh"]

EXPOSE 9200
EXPOSE 9300
EXPOSE 9000
