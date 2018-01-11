import React from 'react';
import { Button, Card, Label} from 'semantic-ui-react';
import {cardStyles as styles} from '../../utils/styles';
import { TagEditContainer } from '../TagEdit';

const TagCard = ({tag, references, onEdit, onDelete, onSubmit}) => (
    <Card>
        <Card.Content>
            <Card.Header style={styles.cardHeader}>
                {tag.name}
            </Card.Header>
            <Card.Description>
                <Label>
                    This tag is associated with {tag.referencesIds.length} references
                </Label>
            </Card.Description>
            <Card.Content style={styles.cardExtra} extra>
                <div className='ui two buttons'>
                    <TagEditContainer tag={tag}
                                      references={references}
                                      onSubmit={onEdit}
                                      headerText='Edit tag'>
                        Edit
                    </TagEditContainer>

                    <Button basic color='red' onClick={onDelete}>
                        Delete
                    </Button>
                </div>
            </Card.Content>

        </Card.Content>
    </Card>
);

export default TagCard;
