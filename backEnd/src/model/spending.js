const userModel = require('./user');
const dbInstance = require("./dbConnect");

/**
 * Retrieves all-time income for the given user, broken down by secondary category, asynchronously.
 * @param email The email of the user to get income for.
 * @returns {Promise<*>} A promise containing a success status and a data object containing all the secondary
 * income categories their values, and counts of transactions in that category.
 */
async function incomeByCategoryForUser(email){
    const response = await userModel.getUserIDfromEmailAsync(email);
    if(response.success){
        const QUERY = `SELECT category_primary, category_secondary, count(amount) as count, sum(amount) \
        as total FROM transactions WHERE user_id = ${response.userId} and amount < 0 GROUP BY category_secondary`;
        let results = await dbInstance.queryAsync(QUERY);
        if(!results.err){
            results.rows.forEach( function(row) {
                // Amount will be negative because it represents a negative expense, but I want positive b/c it's more intuitive
                row.total *= -1;
            });
            return {success:true, data:results.rows};
        } else {
            return {success:false, message: results.err};
        }
    } else {
        return response;
    }
}

/**
 * Retrieves all-time expenses for the given user, broken down by secondary category, asynchronously.
 * @param email The email of the user to get expenses for.
 * @returns {Promise<*>} A promise containing a success status and a data object containing all the secondary
 * expenses categories their values, and counts of transactions in that category.
 */
async function spendingByCategoryForUser(email){
    const response = await userModel.getUserIDfromEmailAsync(email);
    if(response.success){
        const QUERY = `SELECT category_primary, category_secondary, count(amount) as count, sum(amount) \
        as total FROM transactions WHERE user_id = ${response.userId} and amount > 0 GROUP BY category_secondary`;
        let results = await dbInstance.queryAsync(QUERY);
        if(!results.err){

            return {success:true, data:results.rows};
        } else {
            return {success:false, message: results.err};
        }
    } else {
        return response;
    }
}

/**
 * Retrieves expenses for the given user, broken down by secondary category, for a given month, asynchronously.
 * @param email The email of the user to get expenses for.
 * @month The month to get data for. Format must be "YYYY-MM" e.g "2018-04".
 * @returns {Promise<*>} A promise containing a success status and a data object containing all the secondary
 * expenses categories their values, and counts of transactions in that category.
 */
async function spendingByCategoryForUserForMonth(email, month){
    const response = await userModel.getUserIDfromEmailAsync(email);
    if(response.success){
        const QUERY = `SELECT category_primary, category_secondary, count(amount) as count, sum(amount) \
        as total FROM transactions WHERE user_id = ${response.userId} and amount > 0 \
        and date_format(date, '%Y-%m') = '${month}' GROUP BY category_secondary`;
        let results = await dbInstance.queryAsync(QUERY);
        if(!results.err){

            return {success:true, data:results.rows};
        } else {
            return {success:false, message: results.err};
        }
    } else {
        return response;
    }
}

/**
 * Retrieves income for the given user, broken down by secondary category, for a given month, asynchronously.
 * @param email The email of the user to get income for.
 * @param month The month to get data for. Format must be "YYYY-MM" e.g "2018-04".
 * @returns {Promise<*>} A promise containing a success status and a data object containing all the secondary
 * income categories their values, and counts of transactions in that category.
 */
async function incomeByCategoryForUserForMonth(email, month){
    const response = await userModel.getUserIDfromEmailAsync(email);
    if(response.success){
        const QUERY = `SELECT category_primary, category_secondary, count(amount) as count, sum(amount) \
        as total FROM transactions WHERE user_id = ${response.userId} and amount < 0 \
        and date_format(date, '%Y-%m') = '${month}' GROUP BY category_secondary`;
        let results = await dbInstance.queryAsync(QUERY);
        if(!results.err){
            results.rows.forEach( function(row) {
                // Amount will be negative because it represents a negative expense, but I want positive b/c it's more intuitive
                row.total *= -1;
            });
            return {success:true, data:results.rows};
        } else {
            return {success:false, message: results.err};
        }
    } else {
        return response;
    }
}

/**
 * Retrieves cash-flow information for the given user, for all-time, asynchronously
 * @param email email The email of the user to get data for.
 * @returns {Promise<*>} A promise containing a succes status and a data object containing the summed income and expense
 * data per month for the given user.
 */
async function cashFlowForUser(email){
    let expenses = [];
    let incomes = [];
    const response = await userModel.getUserIDfromEmailAsync(email);
    if(response.success){
        const INCOMEQUERY = `SELECT date_format(date, '%Y-%m') as month, sum(amount) as total FROM transactions \
         WHERE user_id = ${response.userId} and amount < 0 GROUP by month`;
        const EXPENSEQUERY = `SELECT date_format(date, '%Y-%m') as month, sum(amount) as total FROM transactions \
         WHERE user_id = ${response.userId} and amount > 0 GROUP by month`;
        let incomeResults = await dbInstance.queryAsync(INCOMEQUERY);
        let expenseResults = await dbInstance.queryAsync(EXPENSEQUERY);
        if(!incomeResults.err && !expenseResults.err) {
            incomeResults.rows.forEach(function (row) {
                incomes.push({month: row.month, value: -row.total});
            });
            expenseResults.rows.forEach(function (row) {
                expenses.push({month: row.month, value: -row.total});
            });
            return {success: true, data: {income: incomes, expense: expenses}};
        }
    } else {
        return {success:false, message: incomeResults.err+" "+expenseResults.err};
    }
}

/**
 * Retrieves the months for which the user has any transactions, and therefore cash-flow data.
 * @param email The email of the user to retrieve data for.
 * @returns {Promise<*>} A promise containing a success status and a data object containing a list of month strings
 * that represent valid months for this user, e.g ['2017-12','2018-01']
 */
async function getValidMonths(email){
    let userResponse = await userModel.getUserIDfromEmailAsync(email);
    if(userResponse.success) {
        const QUERY = `SELECT DISTINCT date_format(date, '%Y-%m') AS month FROM transactions \
                        where user_id = ${userResponse.userId} ORDER BY date asc`;
        const result = await dbInstance.queryAsync(QUERY);
        if(!result.err){
            return {success:true, data:result.rows.map(function(row){
                return row.month;
            })};
        } else {
            return {success:false, message:result.rows};
        }
    } else {
        return userResponse;
    }
}

module.exports = {
    getValidMonths,
    cashFlowForUser,
    spendingByCategoryForUser,
    spendingByCategoryForUserForMonth,
    incomeByCategoryForUser,
    incomeByCategoryForUserForMonth
}