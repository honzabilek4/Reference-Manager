import React, { Component } from 'react';
import _ from 'lodash';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import rest from '../../rest';
import TagCard from '../../components/TagCard/tagCard';
import { TagInsertContainer } from '../../components/TagEdit';

class TagsView extends Component {
    editTag = (id) => (values) => {
        console.log(`Edited tag with id ${id} and values `, values);
    };
    deleteTag = (id) => () => {
        const {dispatch} = this.props;
        dispatch(rest.actions.tag.delete({id: id}));
    };
    onInsertNewTag = (values) => {

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
                    <TagInsertContainer onSubmit={this.onInsertNewTag}
                                        references={references.data}
                                        headerText={'Insert new tag'}>
                        Insert new tag
                    </TagInsertContainer>
                </div>

                {_.map(tags.data, tag => {
                    return <TagCard key={tag.id}
                                    tag={tag}
                                    references={references.data}
                                    onDelete={this.deleteTag(tag.id)}
                    />;
                })}
            </div>
        );
    }
}

TagsView.propTypes = {
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

const ConnectedTagsView = connect(mapStateToProps)(TagsView);

export { ConnectedTagsView as TagsView };
