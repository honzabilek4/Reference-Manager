import React from 'react';
import PropTypes from 'prop-types';
import { Button, Card, Checkbox, List } from 'semantic-ui-react';
import ReferenceCardListItem from './referenceCardListItem';
import ReferenceEdit from '../ReferenceEdit';
import referencePropType from '../../utils/referencePropTypes';
import ReferenceCardListTagItem from './referenceCardListTagItem';
import {cardStyles as styles} from '../../utils/styles';

const ReferenceCard = ({reference, tags, allTags, onEdit, onDelete, onSubmit}) => (
    <Card>
        <Card.Content style={styles.cardContent}>
            <Checkbox style={styles.checkbox}/>
            <Card.Header style={styles.cardHeader}>{reference.title}</Card.Header>
            <Card.Meta>{reference.authors}</Card.Meta>
            <Card.Description>
                <List>
                    <ReferenceCardListItem label='Venue:' icon='home' content={reference.venue}/>
                    <ReferenceCardListItem label='Pages:' icon='book'
                                           content={`${reference.pagesStart}-${reference.pagesEnd}`}/>
                    <ReferenceCardListTagItem tags={tags}/>
                </List>
            </Card.Description>
            <Card.Content style={styles.cardExtra} extra>
                <div className='ui two buttons'>
                    <ReferenceEdit reference={reference}
                                   tags={allTags}
                                   onSubmit={onEdit}
                                   headerText='Edit reference'>
                        Edit
                    </ReferenceEdit>

                    <Button basic color='red' onClick={onDelete}>
                        Delete
                    </Button>
                </div>
            </Card.Content>
        </Card.Content>
    </Card>
);

ReferenceCard.propTypes = {
    reference: referencePropType,
    onEdit: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired
};

export default ReferenceCard;
