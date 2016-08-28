/**
 * 
 */

var data = [
  {id: 1, name: "test1", path: "/tmp/path1"},
  {id: 2, name: "test2", path: "/tmp/path2"}
];

var ImageRow = React.createClass({
    render: function() {
        return(
                <tr key={this.props.id}>
                    <td>{this.props.id}</td>
                    <td>{this.props.name}</td>
                    <td>{this.props.path}</td>
                </tr>
        );
    }
});


var ImageTable = React.createClass({
    render: function() {
        var images = this.props.images.map(function(img) {
            return(<ImageRow key={img.id} id={img.id} name={img.name} path={img.path} />);
        });
        
        return(
                <div className="imageTable">
                    <table>
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Path</th>
                            </tr>
                        </thead>
                        <tbody>
                            {images}
                        </tbody>
                    </table>
                </div>
        );
    }
});

ReactDOM.render(
        <ImageTable images={data} />,
        document.getElementById('container')
);