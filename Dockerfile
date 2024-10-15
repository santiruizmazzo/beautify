FROM ubuntu:rolling

WORKDIR /app

COPY . .

ARG PORT=8000
EXPOSE $PORT

ENV PORT=$PORT

RUN apt update
RUN DEBIAN_FRONTEND=noninteractive apt install -y curl
RUN DEBIAN_FRONTEND=noninteractive apt install -y zip
RUN curl -s https://get.sdkman.io | bash
RUN chmod +x install_grails
RUN chmod +x start_app

CMD ["/bin/bash"]