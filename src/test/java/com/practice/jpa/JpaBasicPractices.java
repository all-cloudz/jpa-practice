package com.practice.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.practice.jpa.domain.entity.Member;
import com.practice.jpa.domain.entity.Team;
import com.practice.jpa.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
class JpaBasicPractices {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void practice1_checkFirstLevelCache() {
        // given
        final Team testTeam = createTestTeam();

        // when
        testEntityManager.persist(testTeam);

        // then
        final Team actualFromFirstLevelCache = testEntityManager.find(Team.class, testTeam.getId());
        assertThat(actualFromFirstLevelCache).________;

//        testEntityManager.clear();
//        final Team actualFromDatabaseBeforeFlush = testEntityManager.find(Team.class, testTeam.getId());
//        assertThat(actualFromDatabaseBeforeFlush).________;

        testEntityManager.flush();
        testEntityManager.clear();
        final Team actualFromDatabaseAfterFlush = testEntityManager.find(Team.class, testTeam.getId());
        assertThat(actualFromDatabaseAfterFlush).________;
    }

    @Test
    void practice2_insertMemberManually() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        try {
            // given
            final Member member = createTestMember();

            // when
            entityManager.persist(member);
            entityManager.clear();

            // then
            final Member actual = entityManager.find(Member.class, member.getId());
            assertThat(actual).________;
            tx.________;
        } catch (Exception e) {
            tx.________;
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Test
    void practice3_insertMemberAutomatically() {
        // given
        final Member member = createTestMember();

        // when
        memberRepository.save(member);
        testEntityManager.clear();

        // then
        final Member actual = testEntityManager.find(Member.class, member.getId());
        assertThat(actual).________;
    }

    @Test
    void practice4_watchInsertQueries() {
        // given
        final Member member = createTestMember();
        final Team team = createTestTeam();

        // when
        testEntityManager.________(member);
        testEntityManager.________(team);
        testEntityManager.clear();

        // then
        final Member actualMember = testEntityManager.find(Member.class, member.getId());
        assertThat(actualMember).________;

        final Team actualTeam = testEntityManager.find(Team.class, team.getId());
        assertThat(actualTeam).________;
    }

    @Test
    void practice5_checkWriteBuffer() {
        // given
        final Member member = createTestMember();
        final Team team = createTestTeam();

        // when
        testEntityManager.persist(member);
        testEntityManager.persist(team);

        testEntityManager.flush();
        testEntityManager.clear();

        // then
        final Member actualMember = testEntityManager.find(Member.class, member.getId());
        assertThat(actualMember).________;

        final Team actualTeam = testEntityManager.find(Team.class, team.getId());
        assertThat(actualTeam).________;
    }

    @Test
    void practice6_updateMemberManually() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        try {
            // given
            final Member member = createTestMember();
            entityManager.persist(member);
            entityManager.clear();

            // when
            final Member found = entityManager.find(Member.class, member.getId());
            found.updateName("update-test");
            entityManager.________;
            entityManager.clear();

            // then
            final Member actual = entityManager.find(Member.class, found.getId());
            assertThat(actual.getName()).isEqualTo("update-test");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Test
    void practice7_updateMemberAutomatically() {
        // given
        final Member member = createTestMember();
        memberRepository.________(member);
        testEntityManager.clear();

        // when
        final Member found = memberRepository.findById(member.getId())
                                             .orElseThrow(RuntimeException::new);
        found.updateName("update-test");
        memberRepository.________;
        testEntityManager.clear();

        // then
        final Member actual = memberRepository.findById(found.getId())
                                              .orElseThrow(RuntimeException::new);
        assertThat(actual.getName()).________;
    }

    private static Member createTestMember() {
        return Member.builder()
                     .name("test")
                     .email("test@naver.com")
                     .build();
    }

    private static Team createTestTeam() {
        return Team.builder()
                   .id(1L)
                   .name("TeamA")
                   .build();
    }
}
