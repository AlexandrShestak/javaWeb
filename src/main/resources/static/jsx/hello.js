if(typeof window !== 'undefined') {
    $( document ).ready(function() {
        React.render(<Hello/>, document.getElementById("container"));
    });
}


var Hello = React.createClass({
    handleClick: function(event) {
        alert("hello!!!");
    },
    render: function () {
        return (
            <div className="col-md-4">
                <p><a className="btn btn-default" href="#" role="button" onClick={ this.handleClick }>Hello from javascript server side rendering on java</a></p>
            </div>
        )
    }
});

