# syntax=docker/dockerfile:1

FROM ubuntu
FROM node
ENV NODE_ENV=production

WORKDIR /app/

COPY ["package.json", "package-lock.json", "./"]

RUN npm install --production

RUN apt-get update && \
    apt-get -y install sudo

RUN sudo apt install build-essential

COPY . .

CMD [ "node", "app.js" ]
