import React from 'react';
import { Button, Form, Modal } from 'semantic-ui-react';
import { CheckboxField, InputField } from 'react-semantic-redux-form';
import { Field, reduxForm, initialize } from 'redux-form';
import _ from 'lodash';

const TagInsertContainer = ({children, headerText, submitButtonText, onSubmit, references}) => (
  <TagInsertForm        onSubmit={onSubmit}
                        headerText={headerText}
                        submitButtonText={submitButtonText}
                        references={references}>
      {children}
  </TagInsertForm>
);

const TagEditContainer = ({children, references, tag, headerText, submitButtonText, onSubmit}) => {
    const referencesToFields = {};
    tag.referencesIds.forEach((value) => {
        referencesToFields[`reference-${value}`] = true;
    });
    return (<TagEditForm
        references={references}
        headerText={headerText}
        initialValues={{
            name: tag.name,
            ...referencesToFields
        }}>
        {children}
    </TagEditForm>);
};

class TagEdit extends React.Component {
    state = {modalOpen: false};
    handleOpen = () => {
        const {dispatch, initialValues} = this.props;
        dispatch(initialize('TagEdit', initialValues));
        this.setState({modalOpen: true})
    };
    handleClose = () => {
        this.setState({modalOpen: false})
    };

    openModalButton = () => (
        <Button basic color='black' onClick={this.handleOpen}>
            {this.props.children}
        </Button>
    );

    render () {
        const {handleSubmit, headerText, references} = this.props;
        return (
            <Modal trigger={this.openModalButton()}
                   open={this.state.modalOpen}
                   onClose={this.handleClose}
                   closeIcon>
                <Modal.Header>
                    {headerText}
                </Modal.Header>
                <Modal.Content>
                    <Form onSubmit={handleSubmit}>
                        <Field name='name' component={InputField}
                               label='Name'/>

                        {_.map(references, reference => {
                            return (
                                <Field name={`reference-${reference.id}`}
                                       component={CheckboxField}
                                       label={reference.title}
                                />
                            );
                        })}

                        <Button fluid
                                color='green'
                                onClick={this.handleClose}>
                            Submit</Button>
                    </Form>
                </Modal.Content>
            </Modal>
        );
    }
}

const TagEditForm = reduxForm({
    form: 'TagEdit'
})(TagEdit);

const TagInsertForm = reduxForm({
    form: 'TagInsert'
})(TagEdit);

export { TagEditContainer, TagInsertContainer };
