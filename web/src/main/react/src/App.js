import React from 'react';
import { Container, Menu } from 'semantic-ui-react';
import { HOME_PATH, LOGIN_PATH, REFERENCES_PATH, TAGS_PATH } from './router/routes';
import { Link } from 'react-router-dom';
import { withRouter } from 'react-router';

class App extends React.Component {
    render () {
        const homeActive = this.props.location && this.props.location.pathname === HOME_PATH;
        const referencesActive = this.props.location && this.props.location.pathname === REFERENCES_PATH;
        const tagsActive = this.props.location && this.props.location.pathname === TAGS_PATH;
        const loginActive = this.props.location && this.props.location.pathname === LOGIN_PATH;

        return (
            <div>
                <Menu fixed='top' inverted>
                    <Container>
                        <Menu.Item header as={Link} to={HOME_PATH}>
                            Reference Manager
                        </Menu.Item>
                        <Menu.Item as={Link} to={HOME_PATH} active={homeActive}>
                            Home
                        </Menu.Item>
                        <Menu.Item as={Link} to={REFERENCES_PATH} active={referencesActive}>
                            References
                        </Menu.Item>
                        <Menu.Item as={Link} to={TAGS_PATH} active={tagsActive}>
                            Tags
                        </Menu.Item>
                        <Menu.Item as={Link} to={LOGIN_PATH} active={loginActive}>
                            Login
                        </Menu.Item>
                    </Container>
                </Menu>
            </div>
        );
    }
}

export default withRouter(App);
