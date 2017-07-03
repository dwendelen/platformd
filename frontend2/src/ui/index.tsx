import './styles.css';
import './960.css';
import './960_24.css';
import registerServiceWorker from './registerServiceWorker';

import * as React from 'react';
import * as ReactDOM from 'react-dom';
import {Provider} from 'react-redux';

import {store} from '../core/store';
import {App} from './App';

ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById('root') as HTMLElement
);
registerServiceWorker();
