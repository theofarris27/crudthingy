object RegistrationUtil {

    var existingUsernames=listOf<String>("Billy")
    var existingEmails= listOf<String>("bingbong@gmail.com")

    fun validateUsername(username:String):Boolean{
        return ! (existingUsernames.contains(username)) && username.isNotEmpty()
    }
    fun validatePassword(password:String, confirmPassword:String):Boolean{
        val holder=password.lowercase()
        if ((password==confirmPassword)&&(password.length>=8)&&
            holder!=password){

            for(char in password){
                if(char.isDigit()) return true
            }
        }
        return false
    }

    fun validateName(name:String):Boolean{
        return name.isNotEmpty()
    }

    fun validateEmail(email:String):Boolean{
        return (email.isNotEmpty()&&!existingEmails.contains(email)&&email.contains('@')&&
                email.contains('.')&&
                email.substring(email.indexOf('@')+1,email.lastIndexOf('.')).all { char->char.isLetter() }&&
                email.substring(email.lastIndexOf('.')+1,email.length).all { char->char.isLetter() }&&
                email.substring(email.indexOf('.')+1,email.length) == "com"


                //"thingy"
                // 123456
                // 012345
                )
    }

}