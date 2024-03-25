import DataTable from 'react-data-table-component';
import React from "react";

const List = ({ list, deleteUser, deleteManyUsers, changeForm, updateUser }: any) => {
    const [selectedRows, setSelectedRows]: any = React.useState(false);
    const [toggledClearRows] = React.useState(false);

    const handleChange = ({ selectedRows }: any) => {
        setSelectedRows(selectedRows);
    };

    const clickUpdate = ({ data }: any) => {
        updateUser(data);
    };

    const customSort = (rows: any, selector: any, direction: any) => {
        return rows.sort((rowA: any, rowB: any) => {
            const aField = selector(rowA)
            const bField = selector(rowB)

            let comparison = 0;
            if (aField.props || bField.props) {
                if (aField.props.checked) {
                    comparison = 1;
                } else if (bField.props.checked) {
                    comparison = -1;
                }
            } else {
                if (aField > bField) {
                    comparison = 1;
                } else if (aField < bField) {
                    comparison = -1;
                }
            }

            return direction === 'desc' ? comparison * -1 : comparison;
        });
    };

    const columns = [
        {
            name: 'ID',
            selector: (row: any) => row.id,
            sortable: true
        },
        {
            name: 'Username',
            selector: (row: any) => row.username,
            sortable: true
        },
        {
            name: 'Email',
            selector: (row: any) => row.email,
            sortable: true
        },
        {
            name: 'Image',
            selector: (row: any) => row.photo,
            sortable: true
        },
        {
            name: 'Role',
            selector: (row: any) => row.role,
            sortable: true
        },
        {
            name: 'Enabled',
            selector: (row: any) => <input type="checkbox" checked={row.enabled}
                onChange={() => clickUpdate(
                    { data: { enabled: !row.enabled , id: row.id } })
                }
            />,
            sortable: true,
        },
        {
            name: 'Operations',
            selector: (row: any) =>
                <div>
                    <button type="button" className='btn btn-info me-2' onClick={() => {
                        changeForm(row, "update")
                    }}>Update</button>
                    <button type="button" className='btn btn-danger' onClick={() => {
                        deleteUser(row.id)
                    }}>Delete</button>
                </div>,
            sortable: false
        },
    ];

    return (
        <div>
            <button type='button' className='btn btn-danger mt-2 mb-2 ms-2' disabled={selectedRows.length === 0} onClick={() => {
                deleteManyUsers(selectedRows)
            }}>Delete selected</button>
            {
                <DataTable
                    sortFunction={customSort}
                    columns={columns}
                    data={list}
                    pagination
                    selectableRows
                    onSelectedRowsChange={handleChange}
                    clearSelectedRows={toggledClearRows}
                />
            }
        </div>
    );
}

export default List;