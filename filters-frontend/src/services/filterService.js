import _ from "lodash";
import axios from 'axios';

/**
 * Get a list of all filters with their properties from the back-end.
 */
export async function getFilters() {
    const filtersResponse = await axios.get("/filters");
    return filtersResponse.data;
}

/**
 * Save a copy of the provided filter object with criteria to the
 * back-end. Only the necessary fields are sent and any solely
 * client-side variables are ignored.
 */
export async function saveFilter(originalFilter) {
    let filter = _.pick(originalFilter, 'name', 'matchType');
    filter.criteria = [];

    originalFilter.criteria.forEach((criterion) => {
        filter.criteria.push(_.pick(criterion, 'type', 'operator', 'value'));
    });

    await axios.post("/filters", filter);
}
