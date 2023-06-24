import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import Instance from '../util/axiosConfig';

export default function ReadOnlyBoard() {
  const [forumList, setForumList] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchData(currentPage);
  }, [currentPage]);

  const fetchData = (page) => {
    Instance.get(`/boards/forum/?page=${page}`)
      .then((res) => {
        setForumList(res.data.content);
        setTotalPages(res.data.totalPages);
      })
      .catch((err) => console.log(err));
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
    <>
      <div className='boardListBox roundedRectangle darkModeElement'>
        <table className='boardList'>
          <thead>
            <tr>
              <th>No</th>
              <th>Title</th>
              <th>Name</th>
              <th>Date</th>
              <th>View</th>
            </tr>
          </thead>
          <tbody>
            {forumList.map((forum) => (
              <tr key={forum.postNo}>
                <td>{forum.postNo}</td>
                <td>
                  <Link to={`/boards/${forum.postNo}`} className='sub'>
                    {forum.title}
                  </Link>
                  <Link to={`/boards/${forum.postNo}`} className='comm'>
                    {' '}
                    [{forum.commentCount || 0}]
                  </Link>
                </td>
                <td>{forum.userNick}</td>
                <td>{new Date(forum.postDate).toLocaleDateString()}</td>
                <td>{forum.views}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className='pagination'>
          {Array.from({ length: totalPages }, (_, index) => index + 1)
            .filter((page) => Math.abs(page - currentPage) <= 2)
            .map((page) => (
              <button
                key={page}
                className={page === currentPage ? 'active' : ''}
                onClick={() => handlePageChange(page)}
              >
                {page}
              </button>
            ))}
        </div>
      </div>
    </>
  );
}
