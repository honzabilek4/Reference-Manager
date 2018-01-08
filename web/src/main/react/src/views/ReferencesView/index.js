import React, { Component } from 'react';
import { Card } from 'semantic-ui-react';
import _ from 'lodash';
import ReferenceCard from '../../components/ReferenceCard';
import { ReferenceInsertContainer } from '../../components/ReferenceEdit';
import ReferenceImport from '../../components/ReferenceEdit/referenceImport';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import rest from '../../rest';

class ReferencesView extends Component {
    editReference = (id) => (values) => {
        console.log(`Edited reference with id ${id} and values `, values);
    };
    deleteReference = (id) => () => {
        console.log(`Deleted reference with id ${id}`);
    };
    onInsertNewReference = (values) => {

    };

    componentDidMount () {
        const {dispatch} = this.props;
        dispatch(rest.actions.tags.sync());
        dispatch(rest.actions.references.sync());
    }

    render () {
        const {tags, references} = this.props;
        if (tags.loading || !tags.data || references.loading || !references.data) {
            return (<div>
                Loading data...
            </div>);
        }

        return (
            <div>
                <div style={{
                    paddingBottom: 10,
                    display: 'flex',
                    flexDirection: 'row',
                }}>
                    <ReferenceInsertContainer onSubmit={this.onInsertNewReference} tags={tags.data}
                                              headerText='Insert new reference'>
                        Insert new reference
                    </ReferenceInsertContainer>
                    <ReferenceImport/>
                </div>

                <Card.Group>
                    {_.map(references.data, reference => {
                        const tagsForReference = _.filter(tags.data, tag => {
                            const tagReferenceIds = _.map(tag.references, tagReference => {
                                return {id: tagReference.id};
                            });
                            const matchingTagReferences = _.filter(tagReferenceIds, tagReferenceId => {
                               return tagReferenceId.id === reference.id;
                            });
                            return matchingTagReferences.length > 0;
                        });
                        return <ReferenceCard key={reference.id}
                                              reference={reference}
                                              tags={tagsForReference}
                                              allTags={tags.data}
                                              onEdit={this.editReference(reference.id)}
                                              onDelete={this.deleteReference(reference.id)}
                        />;
                    })
                    }
                </Card.Group>
            </div>
        );
    }
}

ReferencesView.propTypes = {
    tags: PropTypes.shape({
        loading: PropTypes.bool.isRequired,
        data: PropTypes.shape.isRequired
    }),
    dispatch: PropTypes.func.isRequired
};

const mapStateToProps = (state) => {
    return {
        tags: state.tags,
        references: state.references
    };
};

const ConnectedReferencesView = connect(mapStateToProps)(ReferencesView);

export { ConnectedReferencesView as ReferencesView };
