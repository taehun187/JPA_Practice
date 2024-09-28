# JPA Inheritance Example

## 프로젝트 설명
이 프로젝트는 JPA의 상속 전략을 사용하여 앨범, 책, 영화 등의 항목(Item)을 관리하는 예제입니다. `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)` 전략을 통해 각각의 자식 클래스(Album, Book, Movie)가 독립된 테이블로 관리됩니다.

## 주요 기능
- 상속을 통한 엔티티 관리
- 각 엔티티에 따른 저장 및 조회
- `@DynamicUpdate`, `@DynamicInsert`를 사용하여 필요한 필드만 업데이트 및 삽입

## 클래스 설명

### 1. `Item` (추상 클래스)
- 공통 속성: `id`, `price`, `name`
- 상속 전략: `TABLE_PER_CLASS`
- 자식 클래스들이 상속받아 사용할 수 있는 추상 클래스입니다.

### 2. `Album` (앨범)
- 추가 속성: `artist` (아티스트 이름)
- `@DiscriminatorValue("ALBUM")`로 구분됩니다.

### 3. `Book` (책)
- 추가 속성: `author` (저자), `isbn` (ISBN)
- `@DiscriminatorValue("BOOK")`로 구분됩니다.

### 4. `Movie` (영화)
- 추가 속성: `director` (감독), `actor` (배우)
- `@DiscriminatorValue("MOVIE")`로 구분됩니다.

### 5. `Main`
- JPA를 활용하여 엔티티를 저장하고 조회하는 예제 코드가 포함되어 있습니다.

## 실행 예시
Album album = new Album();
album.setName("haris");
album.setPrice(15000);
album.setArtist("kim");
em.persist(album);

Book book = new Book();
book.setName("haris");
book.setPrice(15000);
book.setAuthor("kim");
book.setIsbn("12345");
em.persist(book);

// 저장된 데이터 조회
Item item = em.find(Item.class, book.getId());

