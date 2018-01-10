
import PropTypes from 'prop-types';

const referencePropType = PropTypes.shape({
    id: PropTypes.number.isRequired,
    title: PropTypes.string.isRequired,
    authors: PropTypes.arrayOf(PropTypes.string.isRequired)
        .isRequired,
    venue: PropTypes.string,
    pagesStart: PropTypes.number,
    pagesEnd: PropTypes.number,
    notes: PropTypes.arrayOf(PropTypes.shape().isRequired).isRequired,
    tags: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired
    }))
});

export default referencePropType;
