FROM gradle:6.5.1-jdk11

WORKDIR /usr/src/ua-tuto
COPY utils/ ./utils/
COPY eai-validation/ ./eai-validation/
COPY obsolete-validation/ ./obsolete-validation/
RUN rm -rf ./*/*gradle/

WORKDIR /usr/src/ua-tuto/eai-validation
RUN gradle wrapper \
    && ./gradlew build \
    && ./gradlew install

WORKDIR /usr/src/ua-tuto/obsolete-validation
RUN gradle wrapper \
    && ./gradlew build \
    && ./gradlew install

WORKDIR /usr/src/ua-tuto
RUN ln -s ./eai-validation/build/install/eai-validation/bin/eai-validation eai \
    && ln -s ./obsolete-validation/build/install/obsolete-validation/bin/obsolete-validation obsolete \
    && chmod 777 eai ./eai-validation/build/install/eai-validation/bin/eai-validation \
    && chmod 777 obsolete ./obsolete-validation/build/install/obsolete-validation/bin/obsolete-validation
