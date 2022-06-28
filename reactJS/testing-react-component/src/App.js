import { useRef } from 'react';
import styles from './App.module.css';

export default function App() {
  const dialogRef = useRef();

  const showDialog = () => {
    dialogRef.current.showModal();
  }

  return (
    <div className={styles["main-container"]}>
      <button onClick={showDialog} >Open</button>
      <dialog className={styles['dialog-container']} ref={dialogRef}>
        <form method='dialog'>
          <p>Customer Service Kami Menghubungi Anda . . .</p>
          <div className={styles['dialog-button-container']}>
            <button>
              <svg width="75" height="75" viewBox="0 0 75 75" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="37.5" cy="37.5" r="37.5" fill="#2FA869"/>
                <g clipPath="url(#clip0_305_551)">
                <path fillRule="evenodd" clipRule="evenodd" d="M25.5789 23.6524C25.9144 23.3174 26.3173 23.0575 26.7609 22.8899C27.2044 22.7224 27.6786 22.651 28.1518 22.6806C28.625 22.7101 29.0866 22.8399 29.5059 23.0614C29.9252 23.2828 30.2926 23.5908 30.5838 23.965L34.0259 28.387C34.6568 29.1981 34.8793 30.2547 34.63 31.2519L33.5811 35.4515C33.5268 35.669 33.5298 35.8968 33.5896 36.1129C33.6494 36.3289 33.764 36.5258 33.9224 36.6845L38.634 41.3961C38.7928 41.5547 38.9901 41.6696 39.2065 41.7294C39.4229 41.7892 39.6511 41.792 39.8689 41.7374L44.0666 40.6885C44.5587 40.5654 45.0723 40.5559 45.5686 40.6605C46.0649 40.7652 46.531 40.9813 46.9315 41.2925L51.3535 44.7327C52.9432 45.9696 53.0889 48.3186 51.6661 49.7396L49.6833 51.7224C48.2642 53.1414 46.1433 53.7647 44.1663 53.0686C39.1061 51.2881 34.5116 48.3912 30.7238 44.5927C26.9256 40.8055 24.0287 36.2117 22.248 31.1522C21.5538 29.177 22.177 27.0542 23.596 25.6352L25.5789 23.6524Z" fill="white"/>
                </g>
                <defs>
                <clipPath id="clip0_305_551">
                <rect width="30.6818" height="30.6818" fill="white" transform="translate(21.9637 22.6733)"/>
                </clipPath>
                </defs>
              </svg>
              <span>Terima</span>
            </button>
            <button>
              <svg width="75" height="75" viewBox="0 0 75 75" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="37.5" cy="37.5" r="37.5" fill="#FF0043"/>
                <g clipPath="url(#clip0_305_547)">
                <path fillRule="evenodd" clipRule="evenodd" d="M55.7526 39.866C55.7525 40.3401 55.6517 40.8089 55.4568 41.2411C55.2619 41.6734 54.9774 42.0593 54.622 42.3732C54.2667 42.6872 53.8487 42.9221 53.3958 43.0623C52.9428 43.2025 52.4652 43.2448 51.9947 43.1865L46.4334 42.4972C45.4137 42.3704 44.5089 41.7811 43.9795 40.9001L41.7492 37.1903C41.6336 36.9982 41.4704 36.8393 41.2752 36.729C41.0801 36.6186 40.8597 36.5606 40.6356 36.5605L33.9724 36.5649C33.7479 36.5651 33.5272 36.6235 33.332 36.7344C33.1367 36.8453 32.9735 37.0048 32.8582 37.1975L30.6341 40.9088C30.3735 41.3439 30.0173 41.7141 29.5925 41.9914C29.1678 42.2686 28.6855 42.4456 28.1823 42.5091L22.6233 43.207C20.6248 43.4578 18.8597 41.9009 18.8597 39.89L18.8579 37.0859C18.8566 35.0791 19.9143 33.138 21.8039 32.231C26.6395 29.9087 31.9359 28.7049 37.3002 28.709C42.664 28.6977 47.9614 29.8941 52.7997 32.2095C54.6878 33.114 55.7494 35.0551 55.7508 37.0619L55.7526 39.866Z" fill="white"/>
                </g>
                <defs>
                <clipPath id="clip0_305_547">
                <rect width="30.6818" height="30.6818" fill="white" transform="translate(59 38) rotate(134.963)"/>
                </clipPath>
                </defs>
              </svg>
              <span>Tolak</span>
            </button>
          </div>
        </form>
      </dialog>
    </div>
  )
}