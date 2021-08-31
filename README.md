# 더파이러츠 백엔드 개발자 채용과제
## 설치 및 환경설정 가이드
1. java 11 이상
2. h2 등 RDBMS 필요
3. SpringBoot 2.5.4

### pom.xml 의존성 등록 필요
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.4</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.entertest</groupId>
    <artifactId>thepirates</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>thepirates</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>11</java.version>
        <testcontainers.version>1.15.3</testcontainers.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.2.0</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>jakarta.xml.bind</groupId>
            <artifactId>jakarta.xml.bind-api</artifactId>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.testcontainers</groupId>
                <artifactId>testcontainers-bom</artifactId>
                <version>${testcontainers.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```
---
## 테이블 생성 SQL
```
CREATE TABLE Product (
                         product_no	number		NOT NULL,
                         name	varchar(50)		NOT NULL,
                         description	varchar(200)		NOT NULL,
                         date Date
);

CREATE TABLE Delivery (
                          product_no	number		NOT NULL,
                          type	varchar(10)		NOT NULL,
                          closing	varchar(20)		NOT NULL,
                          cost	number		,
                          date	varchar(30)
);

COMMENT ON COLUMN Delivery.type IS 'fast 당일 regular 익일';

CREATE TABLE Options (
                        product_no	number		NOT NULL,
                        name	varchar(100)		NOT NULL,
                        price	number		NOT NULL,
                        stock	number		NOT NULL
);

ALTER TABLE Product ADD CONSTRAINT PK_PRODUCT PRIMARY KEY (
                                                           product_no
    );

ALTER TABLE Delivery ADD CONSTRAINT FK_Product_TO_Delivery_1 FOREIGN KEY (
                                                                          product_no
    )
    REFERENCES Product (
                        product_no
        );

ALTER TABLE Options ADD CONSTRAINT FK_Product_TO_Option_1 FOREIGN KEY (
                                                                      product_no
    )
    REFERENCES Product (
                        product_no
        );
```
---
## API사용가이드
---
## Index
1. [상품추가 API](#상품추가-API)
2. [상품 목록 조회 API](#상품-목록조회-API)
3. [상품 상세조회 API](#상품-상세조회-API)
4. [수령일 선택 목록 API](#수령일-선택-목록-API)
5. [상품 삭제 API](#상품-삭제-API)
---
## 상품추가 API
- 요청주소 : '/product'
- 추가하려는 상품은 POST로 JSON 변환해서 전송할 것

#### 요청 파라미터
- name : 상품명
- description : 상품설명
- delivery: 배송 정보
<br>
&nbsp; - type : 배송방식 -> fast : 당일 배송, regular : 익일 배송
<br>
&nbsp; - closing : 배송 마감 시간
- options : 옵션정보
<br>
&nbsp; - name : 선택 옵션
<br>
&nbsp; - price : 선택한 옵션 가격
<br>
&nbsp; - stock : 선택한 옵션 재고


#### 요청 예시 
```
{
"name": "노르웨이산 연어",
"description": "노르웨이산 연어 300g, 500g, 반마리 필렛", 
"delivery": {
  "type": "fast",
  "closing": "12:00", 
},
"options": [{
  "name": "생연어 몸통살 300g", 
  "price": 10000,
  "stock": 99,
}, {
  "name": "생연어 몸통살 500g", 
  "price": 17000,
  "stock": 99,
}]
}
```
---
## 상품 목록조회 API
- 상품명, 상품설명, 가격 정보를 등록일의 내림차순으로 조회 
- 상품가격은상품옵션의가장낮은가격을보여줌
- 가격정보는 String 으로 반환

#### 요청주소
- 요청주소 : '/product/list'

#### 응답 예시 
```
[
  {
    "name": "완도전복",
    "description": "산지직송 완도 전복 1kg (7미~60미)", 
    "price": "50,000 ~ "
  },
  {
    "name": "노르웨이산 연어",
    "description": "노르웨이산 연어 300g, 500g, 반마리 필렛", 
    "price": "10,000 ~ "
  }
]
```
---
## 상품 상세조회 API
- 상품의 상세 정보(상품명, 상품설명, 배송비, 배송방식, 옵션정보, 상품가격)
#### 요청 파라미터
- productid : 상품 아이디 값

#### 요청주소
- 요청주소 : '/product/list/detail'
- 요청주소 예시 <br>
상품 아이디값이 11일 경우<br>
'/product/list/detail?productid=11'

#### 응답 예시 
```
{
  "name": "노르웨이산 연어",
  "description": "노르웨이산 연어 300g, 500g, 반마리 필렛", 
  "delivery": "fast"
  "options": [
    {
    "name": "생연어 몸통살 300g", 
    "price": 10000,
    }, {
    "name": "생연어 몸통살 500g", 
    "price": 17000,
    } 
  ]
}
```

## 수령일 선택 목록 API
### 상품의 수령일 선택 목록을 조회
- 현재시간을 기준으로 선택 가능한 수령일 목록을 조회
- 수령일 목록은 5일 분량을 보여줌
- 수령일은 배송유형, 마감시간, 휴일 여부에 따라 달라짐

### 요청 파라미터
- productid : 상품 아이디 값

### 요청주소
- 요청주소 : '/product/receiving/date'
- 요청주소 예시 <br>
상품 아이디값이 11일 경우<br>
'/product/receiving/date?productid=11'

### 응답
- 오늘이 금요일 13시 일경우 완도전복의 수령일 선택 옵션은 다음과 같음
- 익일발송(+1) + 마감전(+0) + 배송휴일(토, 일 / +2) + 1일 = 다음주 화요일
부터 시작
- 차주화/수/목/금/토

#### 응답예시
```
[
  {
    "date": "8월 31일 화요일" 
  },
  {
    "date": "9월 1일 수요일"
  }, 
  {
    "date": "9월 2일 목요일" 
  },
  {
    "date": "9월 3일 금요일"
  }, 
  {
    "date": "9월 4일 토요일" 
  }
]
```
## 상품 삭제 API
- 판매가 종료된 상점에 대해 삭제 처리
### 요청 파라미터
- productid : 상품 아이디 값
### 요청주소
- 요청주소 : '/product/{productId}
- 요청주소 예시 <br>
상품 아이디값이 11일 경우<br>
'/product/11'
