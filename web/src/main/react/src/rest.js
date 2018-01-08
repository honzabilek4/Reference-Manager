import "isomorphic-fetch";
import reduxApi, {transformers} from "redux-api";
import adapterFetch from "redux-api/lib/adapters/fetch";

const rest = reduxApi({
    tags: {
        url: `tags/`,
        transformer: transformers.array,
        // base endpoint options `fetch(url, options)`
        options: {
            headers: {
                "Accept": "application/json",
            }
        }
    },
    references: {
        url: `references/`,
        transformer: transformers.array,
        // base endpoint options `fetch(url, options)`
        options: {
            headers: {
                "Accept": "application/json",
            }
        }
    },
    tag: {
        url: `tags/:id`,
        crud: true
    }
});

rest.use("fetch", adapterFetch(fetch)); // it's necessary to point using REST backend
rest.use("rootUrl", "http://localhost:8080/pa165/rest/");
rest.use("responseHandler",
    (err, data)=> {
        if (err) {
            console.log(err);
        } else {
            return data;
        }
});

export default rest;
