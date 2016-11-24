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
    getInitialState: function() {
        return {images: []};
    },
    
    fetchImages: function() {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({images: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    
    componentDidMount: function() {
        this.fetchImages();
        setInterval(this.fetchImages, this.props.pollInterval);
    },
    
    render: function() {
        var imgs = this.state.images.map(function(img) {
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
                            {imgs}
                        </tbody>
                    </table>
                </div>
        );
    }
});

ReactDOM.render(
        <ImageTable url="rest/images/uploaded" pollInterval={5000} />,
        //<ImageTable images={data} />,
        document.getElementById('container')
);