[![CodeFactor](https://www.codefactor.io/repository/github/parkilhoon/data-collector/badge)](https://www.codefactor.io/repository/github/parkilhoon/data-collector)
[![codecov](https://codecov.io/gh/ParkIlHoon/data-collector/branch/main/graph/badge.svg?token=QZUKOYV553)](https://codecov.io/gh/ParkIlHoon/data-collector)
[![CircleCI](https://circleci.com/gh/ParkIlHoon/data-collector/tree/main.svg?style=svg)](https://circleci.com/gh/ParkIlHoon/data-collector/tree/main)
# 여러가지 기술을 찍먹하기 위한 데이터 수집 프로젝트
개발 및 개선 과정을 단계별로 Branch 구성했으며, 구현의도는 주석에, 피드백 내용은 FIXME 주석에 담았습니다.

## 목차
1. [초기 개발 (01_create-collector 브랜치)](https://github.com/ParkIlHoon/data-collector/tree/01_create-collector)
2. [설계 수정 및 피드백 반영 (02_re-design-collector 브랜치)](https://github.com/ParkIlHoon/data-collector/tree/02_re-design-collector)
3. [피드백 반영 (03_fix-feedback 브랜치)](https://github.com/ParkIlHoon/data-collector/tree/03_fix-feedback)
4. [이벤트 Pub-Sub (04_event-pub-sub 브랜치)](https://github.com/ParkIlHoon/data-collector/tree/04_event-pub-sub)
5. [피드백 반영 (05_fix-feedback 브랜치)](https://github.com/ParkIlHoon/data-collector/tree/05_fix-feedback)
6. [ELK Stack 연동 (06_elk-stack 브랜치)](https://github.com/ParkIlHoon/data-collector/tree/06_elk-stack)
7. [피드백 반영 (07 fix-feedback 브랜치)](https://github.com/ParkIlHoon/data-collector/tree/07_fix-feedback)
8. [피드백 반영 (08_fix-feedback 브랜치)](https://github.com/ParkIlHoon/data-collector/tree/08_fix-feedback)
9. [인증인가 처리 (09_authentication 브랜치)](https://github.com/ParkIlHoon/data-collector/tree/09_authentication)

## Swagger
http://127.0.0.1:8080/swagger-ui/index.html

## 구동 방법
1. docker 컨테이너를 띄웁니다.
```shell
docker compose up -d
```
3. `http://127.0.0.1:5601` 으로 접속해 Kibana가 정상적으로 구동되는지 확인합니다.
4. `application.properties` 에서 `writer.file.root-path` 속성을 적절한 경로로 변경합니다.
5. 애플리케이션을 구동합니다.
6. `http://127.0.0.1:8080/data` 로 수집할 데이터를 요청합니다.

## 설계
### 1. 초기 개발
![초기 개발](https://github.com/ParkIlHoon/data-collector/blob/02_re-design-collector/design/1%EC%B0%A8%20%EC%84%A4%EA%B3%84.png)
### 2. 설계 수정 및 피드백 반영
![설계 수정](https://github.com/ParkIlHoon/data-collector/blob/02_re-design-collector/design/2%EC%B0%A8%20%EC%84%A4%EA%B3%84.png)
### 3. 이벤트 pub sub
![3차 설계](https://github.com/ParkIlHoon/data-collector/blob/04_event-pub-sub/design/3%EC%B0%A8%20%EC%84%A4%EA%B3%84.png)
### 4. ELK Stack 연동
![4차 설계](https://github.com/ParkIlHoon/data-collector/blob/06_elk-stack/design/4%EC%B0%A8%20%EC%84%A4%EA%B3%84.png)

## 학습 내용 및 참고 사이트
### 1. 초기 개발
* [AsynchronousFileChannel](https://hbase.tistory.com/44)
* [ByteBuffer](https://forl.tistory.com/137)
### 2. 설계 수정 및 피드백 반영
* [서블릿 컨테이너 관점에서 BIO vs NIO](http://guruble.com/bio-vs-nio/)
* [NIO Zero Copy](https://free-strings.blogspot.com/2016/04/zero-copy.html)
* [Java NIO](https://jeong-pro.tistory.com/145)
* [NIO vs IO](https://dev-coco.tistory.com/42)
* [Spring Scheduler Default Thread Pool Size](https://stackoverflow.com/questions/29796651/what-is-the-default-scheduler-pool-size-in-spring-boot)
* [Spring Scheduler 스레드 풀과 비동기](https://ecsimsw.tistory.com/entry/Scheduler-%EC%A0%81%EC%9A%A9-%EB%B0%B0%EA%B2%BD%EA%B3%BC-%EA%B5%AC%EC%A1%B0-Spring-Scheduler)
### 3. 피드백 반영
* [Queue offer vs put](https://stackoverflow.com/questions/19419805/linkedblockingqueue-put-vs-offer)
* [BlockingQueue drainTo()](https://www.geeksforgeeks.org/blockingqueue-drainto-method-in-java-with-examples/)
### 4. 이벤트 pub sub
* [Spring ApplicationEventPublisher](https://wordbe.tistory.com/entry/Spring-ApplicationEventPublisher)
* [ApplicationEventPublisher는 비동기가 아니다](https://jeong-pro.tistory.com/238)
### 5. 피드백 반영
* [AtomicInteger, 쓰레드 동기화(synchronization) , Atomic, CAS 알고리즘 이해하기](https://truehong.tistory.com/71)
* [싱글 스레드 이벤트 루프 모델](https://medium.com/@sgd.daran/node-js-single-threaded-event-loop-model-dbeccf6a7c34)
### 6. ELK Stack 연동
* [Install Elasticsearch with Docker](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)
* [Install Kibana with Docker](https://www.elastic.co/guide/en/kibana/current/docker.html)
* [Running Logstash on Docker](https://www.elastic.co/guide/en/logstash/current/docker.html)
* [Logstash File input plugin](https://www.elastic.co/guide/en/logstash/current/plugins-inputs-file.html)
* [Logstash Elasticsearch output plugin](https://www.elastic.co/guide/en/logstash/current/plugins-outputs-elasticsearch.html)
* [Elastic 가이드북](https://esbook.kimjmin.net/)
### 7. 피드백 반영
* [Logstash File input plugin - file_completed_log_path](https://www.elastic.co/guide/en/logstash/current/plugins-inputs-file.html#plugins-inputs-file-file_completed_log_path)
* [Logstash Date filter plugin](https://www.elastic.co/guide/en/logstash/current/plugins-filters-date.html)
* [Logstash Output filter plugin - Writing to different indicies](https://www.elastic.co/guide/en/logstash/current/plugins-outputs-elasticsearch.html#_writing_to_different_indices_best_practices)
* [Try-Catch-Finally 사용시 주의사항](https://tomining.tistory.com/154)
### 8. 피드백 반영
* [Logstash Geoip Filter](https://www.elastic.co/guide/en/logstash/current/plugins-filters-geoip.html)
### 9. 인증인가 처리
* [API 인증과 메시지 무결성 확인을 통한 API 보안](https://brunch.co.kr/@sangjinkang/50)
