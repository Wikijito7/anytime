import React, {lazy, Suspense} from 'react';
import ReactDOM from 'react-dom';
import './scss/index.scss';

const App = lazy(() => import('./App'));

ReactDOM.render(
  <React.StrictMode>
    <Suspense fallback={<div>Loading..</div>}>
      <App />
    </Suspense>
  </React.StrictMode>,
  document.getElementById('root')
);

