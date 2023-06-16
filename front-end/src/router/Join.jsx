export default function Join() {
  return (
    <div class='loginNjoinBox roundedRectangle darkModeElement'>
      <form class='formBox' action=''>
        <div class='formInput'>
          <input type='text' name='inputEmail' placeholder='📧이메일' />
          <input
            type='password'
            name='inputPassword'
            placeholder='🔑비밀번호'
          />
          <input
            type='password'
            name='inputPassword'
            placeholder='🔑비밀번호 확인'
          />
          <input type='password' name='inputPassword' placeholder='👤닉네임' />
        </div>
        <button class='btnElement' type='submit'>
          JOIN
        </button>
      </form>
    </div>
  );
}
