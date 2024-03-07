#!/bin/bash
chown -R springboot:springboot /springboot
mvn spring-boot:run
exec "$@";