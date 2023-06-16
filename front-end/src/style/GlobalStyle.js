import { createGlobalStyle } from 'styled-components';
import { Reset } from 'styled-reset';

const GlobalStyle = createGlobalStyle`
  ${Reset};
 
  * {
    box-sizing: border-box;
    }

    body {
    background-color: #f3f0e5;
    color: #000;
    text-decoration: none;
    list-style-type: none;
    display: flex;
    flex-direction: column;
    align-items: center;
    }
`;

export default GlobalStyle;
