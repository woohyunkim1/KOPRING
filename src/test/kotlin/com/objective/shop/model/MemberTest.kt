package com.objective.shop.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

/**
 * created by kim
 */
internal class MemberTest {

    private lateinit var member: Member

    // Member 클래스가 들어갈 배열 생성 (배열 길이 : 0)
    private var memberArray: ArrayList<Member> = ArrayList()


    /**
     * BeforeEach - 각 메서드가 실행하기 전에 실행
     */
    @BeforeEach
    fun setup() {
        member = Member("Test@gmail.com", "test12345!")
        memberArray.add(member)
    }

    /**
     * AfterEach - 각 메서드가 실행 후에 실행
     */
    @AfterEach
    fun clear() {
        memberArray.clear()
    }

    @Test
    fun `비밀번호는 8~20글자`() {
        //영문/숫자/특수문자 사용 글자수 8~20
        val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$"

        //given
        var pw = member.password

        //when
        val PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX)

        val isMatches = PASSWORD_PATTERN.matcher(pw).matches()

        //then
        Assertions.assertThat(isMatches)

    }

    @Test
    fun `이미 존재하는 아이디가 있는 경우 가입 실패`() {
        //given
        var newMember = Member("Hest@gmail.com", "")

        //when
        var result = addMember(newMember)

        //then
        Assertions.assertThat(result).isNotEqualTo("이미 존재하는 아이디입니다.")//값이 존재 하는 경우
    }

    @Test
    fun `이미 존재하는 아이디가 없는 경우 가입 성공`() {
        //given
        var newMember = Member("Test@gmail.com", "")

        //when
        var result = addMember(newMember)

        //then
        Assertions.assertThat(result).isNotEqualTo(newMember.email) // 입력된 값과 같다면, 오류 출력
    }

    //TODO [가입] 서비스 영역에서 구현해야할것 같음
    private fun addMember(member: Member): String {
        if (findByEmail(member.email).isPresent) {
            return "이미 존재하는 아이디입니다."
        } else {
            memberArray.add(member)
            return member.email
        }
    }

    //TODO [조회] 서비스 영역에서 구현해야할것 같음
    private fun findByEmail(email: String): Optional<Member> {
        return memberArray.stream()
            .filter { member -> member.email.equals(email) }
            .findAny()
    }
}
