FROM maven:3.8.5-openjdk-17 as springboot

ARG UID

RUN useradd -G root -u $UID -d /home/springboot springboot

RUN mkdir -p /springboot && \
    chown -R springboot:springboot /springboot

RUN mkdir -p /home/springboot && \
    chown -R springboot:springboot /home/springboot

WORKDIR /springboot

COPY entrypointspringboot.sh /entrypointspringboot.sh

ENTRYPOINT ["/bin/bash", "/entrypointspringboot.sh"]

#########################################################################


FROM node:18.13.0 as react
ARG UID
WORKDIR /var/www

RUN deluser node
RUN useradd -G root -u $UID -d /home/react react
RUN mkdir -p /home/react && \
    chown -R react:react /home/react
USER react


COPY entrypointreact.sh /entrypointreact.sh
ENTRYPOINT ["/bin/bash", "/entrypointreact.sh"]