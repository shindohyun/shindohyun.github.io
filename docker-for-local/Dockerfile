FROM ruby:2.6

ENV LANG=ko_KR.UTF-8
ENV LANGUAGE=ko_KR
ENV TZ=Asia/Seoul
ENV LC_ALL=ko_KR.UTF-8

WORKDIR /app

COPY ../Gemfile ../just-the-docs.gemspec ./
RUN gem install bundler:2.1.4 && bundle _2.1.4_ install

EXPOSE 4000

