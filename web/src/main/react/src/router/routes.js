import React from 'react';
import { Route, Switch } from 'react-router';
import { HomeView, ReferencesView, TagsView } from '../views/index';

const HOME_PATH = '/';
const REFERENCES_PATH = '/references';
const TAGS_PATH = '/tags';

const routes = (
    <Switch>
        <Route exact path={HOME_PATH} component={HomeView}/>
        <Route path={REFERENCES_PATH} component={ReferencesView}/>
        <Route path={TAGS_PATH} component={TagsView}/>
    </Switch>
);

export { routes, HOME_PATH, REFERENCES_PATH, TAGS_PATH };
