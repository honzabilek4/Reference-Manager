// TODO: fix this, make default profile development instead of production
/*
if (process.env.NODE_ENV === 'production') {
    module.exports = require('./configureStore.prod'); // eslint-disable-line global-require
} else {
    module.exports = require('./configureStore.dev'); // eslint-disable-line global-require
}*/

module.exports = require('./configureStore.dev'); // eslint-disable-line global-require
