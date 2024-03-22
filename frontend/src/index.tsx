import React from 'react';
import ReactDOM from 'react-dom/client';
import './provider/media/index.css';
import App from './provider/App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import reducers from './provider/reducers';
import thunk from 'redux-thunk';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

const store = createStore(reducers, applyMiddleware(thunk))

root.render(
  // <React.StrictMode>
  <Provider store={store}>
    <div className='container-fluid' style={{ padding: 0 }}>
      <App />
    </div>
  </Provider>
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
