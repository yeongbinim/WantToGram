package sisibibi.wanttogram.like.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sisibibi.wanttogram.member.entity.MemberEntity;

@Entity
@Table(name = "like")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private MemberEntity memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    public enum EntityType {
        FEED, COMMENT
    }

    public LikeEntity(MemberEntity memberId, EntityType entityType, Long entityId) {
        this.memberId = memberId;
        this.entityType = entityType;
        this.entityId = entityId;
    }
}
