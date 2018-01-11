import React, { Component } from 'react';
import { InputField } from 'react-semantic-redux-form';
import { Button, Form } from 'semantic-ui-react';
import { Field, reduxForm } from 'redux-form';
import { connect } from 'react-redux';
import rest from '../../rest';

class LoginView extends Component {
    submitLogin = (values) => {
        localStorage.setItem("username", values['email']);
        localStorage.setItem("password", values['password']);
        const {dispatch} = this.props;
        dispatch(rest.actions.users.sync(() => {
        }));
        console.log(values);
    };

    render () {
        return (
           <LoginFormContainer
               submitFunction={this.submitLogin}
           />
        );
    }
}

class LoginForm extends Component {
    render () {
        const { error, handleSubmit, submitFunction} = this.props;
        return (
            <Form onSubmit={handleSubmit(submitFunction)}>
                <Field name='email' component={InputField}
                       label='Email'/>

                <Field name='password' component={InputField}
                       label='Password' type='password'/>

                {error && <strong>{error}</strong>}

                <Button fluid
                        color='green'>
                    Login</Button>
            </Form>
        );
    }
}

const LoginFormContainer = reduxForm({
    form: 'LoginForm'
})(LoginForm);

const mapStateToProps = (state) => {
    return {
        users: state.users,
    };
};

const ConnectedLoginView = connect(mapStateToProps)(LoginView);

export default ConnectedLoginView;
