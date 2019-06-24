class AlbumsController < ApplicationController
    before_action :set_artist, only: [:index, :create, :show, :update, :destroy]
    before_action :set_artist_album, only: [:show, :update, :destroy]

    # GET /artists/:artist_id/albums
    def index
        @albums = paginate @artist.albums, per_page: 5
    end

    # GET /artists/:artist_id/albums/:id
    def show
        json_response(@album)
    end

    # POST /artists/:artist_id/albums
    def create
        @album = @artist.albums.create!(album_params)
        response.set_header("Location", artist_album_url(@artist, @album))
        json_response(@album, :created)
    end

    # PUT /artists/:artist_id/albums/:id
    def update
        @album.update!(album_params)
        response.set_header("Location", artist_album_url(@artist, @album))
        head :no_content
    end

    # DELETE /artist/:artist_id/albums/:id
    def destroy
        @album.destroy!
        head :no_content
    end

    def all_albums
        @albums = paginate Album.unscoped, per_page: 5
    end

    private

    def album_params
        params.permit(:title)
    end

    def set_artist
        @artist = Artist.find(params[:artist_id])
    end

    def set_artist_album
        @album = @artist.albums.find_by(id: params[:id]) if @artist
        if @album.nil?
            raise ActiveRecord::RecordNotFound
        end
    end
end
