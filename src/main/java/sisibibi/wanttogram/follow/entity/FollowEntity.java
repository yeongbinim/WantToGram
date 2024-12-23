package sisibibi.wanttogram.follow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sisibibi.wanttogram.member.entity.MemberEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "follow")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
public class FollowEntity {

    // 속성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private MemberEntity follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private MemberEntity following;


    // 생성자

    // 기능
    public void setFollower(MemberEntity member) {
        this.follower = member;
    }

    public void setFollowing(MemberEntity member) {
        this.following = member;
    }
}
