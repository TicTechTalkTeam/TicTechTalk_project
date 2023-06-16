export default function BoardWrite() {
  return (
    <div class='boardWriteBox roundedRectangle darkModeElement'>
      <form class='boardWriteForm'>
        {/* 카테고리는 사용자에게 보이지 않게 처리함(value로 카테고리 번호 넣기) */}
        <input
          type='text'
          name='category'
          class='hideElement'
          placeholder='카테고리 입력란(입력할수 없음)'
        />
        <input
          class='darkModeElement'
          type='text'
          name='title'
          placeholder='제목을 입력하세요.'
        />
        <textarea
          class='darkModeElement'
          name='content'
          cols='30'
          rows='30'
        ></textarea>
        <input
          class='darkModeElement'
          type='text'
          name='link'
          placeholder='참고링크를 입력하세요.'
        />
        <button type='submit' class='btnElement'>
          WRITE
        </button>
      </form>
    </div>
  );
}
