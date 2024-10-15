FROM ubuntu:rolling

WORKDIR /app

COPY . .

EXPOSE 8000

CMD ["/bin/bash"]