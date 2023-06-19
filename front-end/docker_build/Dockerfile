FROM nginx:latest

RUN rm /etc/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY dist /dist
COPY docker_build/nginx.conf /etc/nginx
