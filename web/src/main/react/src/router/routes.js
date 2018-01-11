import React from 'react';
import { Route, Switch } from 'react-router';
import { HomeView, ReferencesView, TagsView } from '../views/index';
import LoginView from '../views/LoginView';

const HOME_PATH = '/';
const REFERENCES_PATH = '/references';
const TAGS_PATH = '/tags';
const LOGIN_PATH = '/login';

const routes = (
    <Switch>
        <Route exact path={HOME_PATH} component={HomeView}/>
        <Route path={REFERENCES_PATH} component={ReferencesView}/>
        <Route path={TAGS_PATH} component={TagsView}/>
        <Route path={LOGIN_PATH} component={LoginView}/>
    </Switch>
);

export { routes, HOME_PATH, REFERENCES_PATH, TAGS_PATH, LOGIN_PATH };
