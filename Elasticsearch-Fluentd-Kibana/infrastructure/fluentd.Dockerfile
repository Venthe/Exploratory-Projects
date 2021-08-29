FROM docker.io/fluent/fluentd:v1.13-1
USER root
RUN apk add --no-cache --update --virtual .build-deps \
        sudo build-base ruby-dev
RUN sudo gem install fluent-plugin-elasticsearch -v '5.1.0' \
 && sudo gem sources --clear-all \
 && apk del .build-deps \
 && rm -rf /tmp/* /var/tmp/* /usr/lib/ruby/gems/*/cache/*.gem
USER fluent