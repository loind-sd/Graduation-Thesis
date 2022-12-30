import React from 'react';
import * as Unicons from '@iconscout/react-unicons';
import './SearchBar.scss'
const SearchBar = (search) => {
    if (search.status === false) {
        return (
            <div className="input-group mt-5">
                <div className="input-group-prepend">
                    <span className="input-group-text" id="search-addon">
                        <Unicons.UilSearchAlt />
                    </span>
                </div>
                <input type={"text"} className="form-control search" placeholder={search.placeholder} />
                <button className='btn btn-light btn-custom input-group-append'>
                    <Unicons.UilUserPlus className='icon-user' />
                </button>
            </div>
        )
    }
    return (
        <div className="input-group mt-5">
            <div className="input-group-prepend">
                <span className="input-group-text" id="search-addon">
                    <Unicons.UilSearchAlt />
                </span>
            </div>
            <input type={"text"} className="form-control search" placeholder={search.placeholder} />
            <div className="input-group-append">
                <button className="input-group-text" id='filter-addon'>
                    <Unicons.UilFilter />
                </button >
            </div>
        </div>
    )
}

export default SearchBar