package CPM.cpm_posting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CPM.cpm_posting.entity.PostingEntity;


@Repository
    public interface PostingRepository extends JpaRepository<PostingEntity, Long> {
    }