class AlbumsController < ApplicationController
    before_action :set_artist
    before_action :set_artist_album, only: [:show, :update, :destroy]

    # GET /artists/:artist_id/albums
    def index
        json_response(@artist.albums)
    end

    # GET /artists/:artist_id/albums/:id
    def show
        json_response(@album)
    end

    # POST /artists/:artist_id/albums
    def create
        @album = @artist.albums.create!(album_params)
        json_response(@album, :created)
    end

    # PUT /artists/:artist_id/albums/:id
    def update
        @album.update(album_params)
        head :no_content
    end

    # DELETE /artist/:artist_id/albums/:id
    def destroy
        @album.destroy
        head :no_content
    end

    private

    def album_params
        params.permit(:title)
    end

    def set_artist
        @artist = Artist.find(params[:artist_id])
    end

    def set_artist_album
        @album = @artist.albums.find_by!(id: params[:id]) if @artist
    end
end
