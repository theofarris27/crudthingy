package com.example.loginandregistration

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {
    @Test
    //methodName_someCondition_expectedResult
    fun validatePassword_emptyPassword_isFalse() {
        val actual = RegistrationUtil.validatePassword("", "")
        //assertThat(actualValue).isEqual(desiredValue)
        assertThat(actual).isFalse()
    }
    @Test
    fun validatePassword_passwordsDontMatch_isFalse() {
        val actual = RegistrationUtil.validatePassword("MrSh0rrsClass","mRSh0rrsClass")
        assertThat(actual).isFalse()
    }
    @Test
    fun validatePassword_goodPasswords_isTrue() {
        var actual = RegistrationUtil.validatePassword("MrSh0rrsClass", "MrSh0rrsClass")
        assertThat(actual).isTrue()
    }

    @Test
    fun validatePassword_containsDigit_isFalse() {
        val actual = RegistrationUtil.validatePassword("MrShorrsClass", "MrShorrsClass")
        assertThat(actual).isFalse()
    }
    @Test
    fun validatePassword_containsCapital_isFalse() {
        val actual = RegistrationUtil.validatePassword("mrsh0rrsclass", "mrsh0rrsclass")
        assertThat(actual).isFalse()
    }
    @Test
    fun validateEmail_goodEmail_isTrue(){
        val actual = RegistrationUtil.validateEmail("theoisawesome1@gmail.com")
        assertThat(actual).isTrue()
    }
    @Test
    fun validateEmail_badEmail_isFalse(){
        val actual = RegistrationUtil.validateEmail("theoisawesome1")
        assertThat(actual).isFalse()
    }
    @Test
    fun validateUsername_goodUsername_isTrue() {
        val actual = RegistrationUtil.validateUsername("theofarris1")
        assertThat(actual).isTrue()
    }
    @Test
    fun validateUsername_badUsername_isFalse() {
        val actual = RegistrationUtil.validateUsername("ab")
        assertThat(actual).isFalse()
    }
}