# WantToGram

## 🫴 Introduce


## 👨‍👨‍👦‍👦 Member

<table align="center">
    <thead>
        <tr>
            <th>👑 팀장</th>
            <th>팀원</th>
            <th>팀원</th>
            <th>팀원</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td align="center"><a href="https://github.com/yeongbinim"><img src="https://github.com/yeongbinim.png" width="100px;" alt=""/></a></td>
            <td align="center"><a href="https://github.com/tmdcksdl"><img src="https://github.com/tmdcksdl.png" width="100px;" alt=""/></a></td>
            <td align="center"><a href="https://github.com/answerin1"><img src="https://github.com/answerin1.png" width="100px;" alt=""/></a></td>
            <td align="center"><a href="https://github.com/Hojin02"><img src="https://github.com/Hojin02.png" width="100px;" alt=""/></a></td>
        </tr>
        <tr>
            <td align="center">임영빈</td>
            <td align="center">이승찬</td>
            <td align="center">김다빈</td>
            <td align="center">김호진</td>
        </tr>
    </tbody>
</table>

## ERD 설계

```mermaid
erDiagram
Member {
	bigint id PK "고유 식별자"
	varchar name "이름"
	varchar email "이메일"
	varchar password "비밀번호"
	datetime created_at "회원가입 시간"
	datetime updated_at "정보변경 시간"
	datetime deleted_at "값이 있으면 삭제된거, 값이 없으면 삭제 안된거"
}

Feed {
	bigint id PK "고유 식별자"
	bigint member_id FK "멤버 참조"
	varchar title "제목"
	varchar content "내용"
	datetime created_at "등록 시간"
	datetime updated_at "수정 시간"
	datetime deleted_at "값이 있으면 삭제된거, 값이 없으면 삭제 안된거"
}

Comment {
	bigint id PK "고유 식별자"
	bigint member_id FK "멤버 참조"
	bigint feed_id FK "피드 참조"
	varchar content "내용"
	datetime created_at "등록 시간"
	datetime updated_at "수정 시간"
}

Follow {
	bigint id PK "고유 식별자"
	bigint follower_id FK "다빈(구독을 요청한 사람)"
	bigint following_id FK "영빈(구독을 요청받은 사람)"
	datetime created_at "등록 시간"
}

Like {
	bigint id PK "고유 식별자"
	bigint member_id FK "멤버 참조"
  enum entity_type "엔티티 타입"
	bigint entity_id "참조 ID"
}

Member ||--o{ Feed : " "
Member ||--o{ Comment : " "
Member ||--o{ Follow : " "
Member ||--o{ Like : " "
Feed ||--o{ Comment : " "

```


## Api 명세서
